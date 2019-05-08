package com.ybj.file.parse.core.excel;

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

import com.ybj.file.config.element.BaseParseFileInfo;
import com.ybj.file.config.element.ToParseFile;
import com.ybj.file.config.element.ToParseTemplate;
import com.ybj.file.parse.core.AbstractReader;
import com.ybj.file.parse.core.ParseException;
import com.ybj.file.parse.core.PostHandleChain;
import com.ybj.file.parse.core.exception.IllegalHeadException;
import com.ybj.file.parse.core.exception.SheetBreakoutException;
import com.ybj.file.parse.core.post.ParseStartHandler;


public class XlsxReader extends AbstractReader {

    private boolean sortedRead;

    @Override
    public void read(BaseParseFileInfo baseFileInfo, ParseStartHandler startHandler) throws ParseException {
        try {
            ToParseFile toParseFile = baseFileInfo.getToParseFile();
            OPCPackage pkg = OPCPackage.open(baseFileInfo.getInputStream());
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = r.getSharedStringsTable();

            XMLReader parser = XMLReaderFactory.createXMLReader("com.sun.org.apache.xerces.internal.parsers.SAXParser");
            XlsxSheetReadHandler handler = new XlsxSheetReadHandler(toParseFile, sst, baseFileInfo.getParseContext());
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
    private void parseWithPriority(ToParseFile toParseFile, XSSFReader r, XlsxSheetReadHandler handler,
            XMLReader parser, ParseStartHandler startHandler) throws InvalidFormatException, IOException,
            SAXException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (ToParseTemplate template : toParseFile.getSortedTemplates()) {
            int sheetIndex = template.getSheetIndex();
            InputStream sheet = r.getSheet("rId" + (sheetIndex + 1));
            handler.setSheetIndex(sheetIndex);
            if (!toParseFile.needParse(sheetIndex)) {
                continue;
            }
            parseSheet(parser, sheet, handler, toParseFile, sheetIndex, startHandler);
        }
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
    private void parseWithIndex(ToParseFile toParseFile, XSSFReader r, XlsxSheetReadHandler handler,
            XMLReader parser, ParseStartHandler startHandler) throws InvalidFormatException, IOException,
            SAXException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        Iterator<InputStream> sheets = r.getSheetsData();
        int i = 0;
        while (sheets.hasNext()) {
            int cur = i;
            i++;
            handler.setSheetIndex(cur);
            InputStream sheet = sheets.next();
            System.out.println("Start parse sheet: " + cur);
            if (!toParseFile.needParse(cur)) {
                continue;
            }
            parseSheet(parser, sheet, handler, toParseFile, cur, startHandler);
        }
    }

    private void parseSheet(XMLReader parser, InputStream sheet, XlsxSheetReadHandler handler, ToParseFile toParseFile,
            int curSheetNo, ParseStartHandler outStartHandler)
            throws IOException, SAXException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        try {
            if (outStartHandler == null) {
               outStartHandler = PostHandleChain.getBuildinChain(toParseFile.getTemplateHandler(curSheetNo)).getStartHandler();
            }
            // 设置该sheet的后置处理器
            handler.setStartHandler(outStartHandler);
            // 初始化该sheet模板的validator以及column的validator
            toParseFile.getTemplateWith(curSheetNo).before();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
        } catch (IllegalHeadException e) {
            System.out.println("表头异常，从当前Sheet" + curSheetNo + "解析中跳出");
        } catch (SheetBreakoutException e) {
            System.out.println("从当前Sheet" + curSheetNo + "解析中跳出");
        } finally {
            sheet.close();
        }
    }

}
