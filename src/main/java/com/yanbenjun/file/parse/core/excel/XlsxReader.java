package com.yanbenjun.file.parse.core.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.yanbenjun.file.config.element.BaseParseFileInfo;
import com.yanbenjun.file.config.element.ToParseFile;
import com.yanbenjun.file.config.element.ToParseTemplate;
import com.yanbenjun.file.parse.core.AbstractReader;
import com.yanbenjun.file.parse.core.ParseException;
import com.yanbenjun.file.parse.core.PostHandleChain;
import com.yanbenjun.file.parse.core.exception.FileBreakoutException;
import com.yanbenjun.file.parse.core.exception.IllegalHeadException;
import com.yanbenjun.file.parse.core.exception.SheetBreakoutException;
import com.yanbenjun.file.parse.core.post.ParseStartHandler;
import com.yanbenjun.file.parse.core.post.TeminationPostRowHandler;
import com.yanbenjun.file.parse.message.HeadParseMessage;
import com.yanbenjun.file.parse.message.ParseMessage;

public class XlsxReader extends AbstractReader {

    private boolean sortedRead;

    @Override
    public void read(BaseParseFileInfo baseFileInfo, ParseStartHandler startHandler) throws ParseException {
        try {
            String fileName = baseFileInfo.getPath();
            ToParseFile toParseFile = baseFileInfo.getToParseFile();
            OPCPackage pkg = OPCPackage.open(fileName);
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = r.getSharedStringsTable();

            XMLReader parser = XMLReaderFactory.createXMLReader("com.sun.org.apache.xerces.internal.parsers.SAXParser");
            XlsxSheetReadHandler handler = new XlsxSheetReadHandler(toParseFile, sst);
            parser.setContentHandler(handler);

            if (!sortedRead) {
                parseWithIndex(toParseFile, r, handler, parser, startHandler);
            } else {
                parseWithPriority(toParseFile, r, handler, parser, startHandler);
            }
        } catch (IOException | SAXException | OpenXML4JException | ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            throw new ParseException(e);
        }
    }

    /**
     * 按照toParseFile中配置的sheet解析优先级（toParseTemplate中的priority字段）来解析sheet
     * 
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     * @throws SAXException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws Exception
     */
    private ParseMessage parseWithPriority(ToParseFile toParseFile, XSSFReader r, XlsxSheetReadHandler handler,
            XMLReader parser, ParseStartHandler startHandler) throws InvalidFormatException, IOException,
            SAXException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        HeadParseMessage hpMsg = new HeadParseMessage();
        for (ToParseTemplate template : toParseFile.getSortedTemplates()) {
            int sheetIndex = template.getSheetIndex();
            InputStream sheet = r.getSheet("rId" + (sheetIndex + 1));
            handler.setSheetIndex(sheetIndex);
            if (!toParseFile.needParse(sheetIndex)) {
                continue;
            }
            parseSheet(parser, sheet, handler, toParseFile, sheetIndex, hpMsg, startHandler);
        }
        return hpMsg;
    }

    /**
     * 未使用，依次从第一个sheet也读取数据
     * 
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     * @throws SAXException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     * @throws Exception
     */
    private ParseMessage parseWithIndex(ToParseFile toParseFile, XSSFReader r, XlsxSheetReadHandler handler,
            XMLReader parser, ParseStartHandler startHandler) throws InvalidFormatException, IOException,
            SAXException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        Iterator<InputStream> sheets = r.getSheetsData();
        int i = 0;
        HeadParseMessage hpMsg = new HeadParseMessage();
        while (sheets.hasNext()) {
            int cur = i;
            i++;
            handler.setSheetIndex(cur);
            InputStream sheet = sheets.next();
            System.out.println("Start parse sheet: " + cur);
            if (!toParseFile.needParse(cur)) {
                continue;
            }
            parseSheet(parser, sheet, handler, toParseFile, cur, hpMsg, startHandler);
        }
        return hpMsg;
    }

    private void parseSheet(XMLReader parser, InputStream sheet, XlsxSheetReadHandler handler, ToParseFile toParseFile,
            int curSheetNo, HeadParseMessage hpMsg, ParseStartHandler outStartHandler)
            throws IOException, SAXException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            if (outStartHandler == null) {
                String handlerStr = toParseFile.getTemplatePostChain(curSheetNo);
                Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(handlerStr);
                try {
                    Class<? extends TeminationPostRowHandler> handlerClazz = clazz
                            .asSubclass(TeminationPostRowHandler.class);
                    outStartHandler = PostHandleChain.getBuildinChain(handlerClazz).getStartHandler();
                } catch (ClassCastException e) {
                    throw new FileBreakoutException();
                }
            }
            handler.setStartHandler(outStartHandler);
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
        } catch (IllegalHeadException e) {
            hpMsg.addAll(e.getHeadParseMessage().getErrorMsgs());
        } catch (SheetBreakoutException e) {
            System.out.println("从当前Sheet" + curSheetNo + "解析中跳出");
        } finally {
            sheet.close();
        }
    }

}
