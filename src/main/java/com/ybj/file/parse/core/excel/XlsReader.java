package com.ybj.file.parse.core.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.IOUtils;

import com.ybj.file.config.element.BaseParseFileInfo;
import com.ybj.file.parse.core.AbstractReader;
import com.ybj.file.parse.core.ParseException;
import com.ybj.file.parse.core.exception.SheetBreakoutException;
import com.ybj.file.parse.core.post.ParseStartHandler;


public class XlsReader extends AbstractReader {

    @Override
    public void read(BaseParseFileInfo baseFileInfo, ParseStartHandler startHandler) throws ParseException {
        // TODO Auto-generated method stub
        InputStream ins = baseFileInfo.getInputStream();
        try {
            POIFSFileSystem poifs = new POIFSFileSystem(ins);
            try {
                // 从流中获取Excel的WorkBook流
                InputStream workBookInputStream = poifs.createDocumentInputStream("Workbook");
                try {
                    HSSFRequest hssfRequest = new HSSFRequest();
                    // 为所有的record注册一个监听器
                    XlsListener xlsListener = new XlsListener(baseFileInfo.getToParseFile(), startHandler,
                            baseFileInfo.getParseContext());
                    MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(xlsListener);
                    FormatTrackingHSSFListener formatListener = new FormatTrackingHSSFListener(listener);

                    if (xlsListener.isOutputFormulaValues()) {
                        SheetRecordCollectingListener workbookBuildingListener = new SheetRecordCollectingListener(
                                formatListener);
                        hssfRequest.addListenerForAllRecords(workbookBuildingListener);
                        xlsListener.setWorkbookBuildingListener(workbookBuildingListener);
                    } else {
                        hssfRequest.addListenerForAllRecords(formatListener);
                    }
                    xlsListener.setFormatListener(formatListener);

                    // 创建事件工厂
                    HSSFEventFactory factory = new HSSFEventFactory();
                    // 根据WorkBook输入流处理所有事件
                    factory.processEvents(hssfRequest, workBookInputStream);
                } finally {
                    workBookInputStream.close();
                }
            } finally {
                poifs.close();
            }
        } catch (SheetBreakoutException e) {

        } catch (IOException e) {
            throw new ParseException(e);
        } finally {
            IOUtils.closeQuietly(ins);
        }
    }

}
