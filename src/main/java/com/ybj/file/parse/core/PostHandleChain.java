package com.ybj.file.parse.core;

import com.ybj.file.model.parse.ParsedRow;
import com.ybj.file.parse.core.exception.RowHandleException;
import com.ybj.file.parse.core.post.FileParseExtractor;
import com.ybj.file.parse.core.post.ParseStartHandler;
import com.ybj.file.parse.core.post.TeminationPostRowHandler;
import com.ybj.file.parse.core.post.infs.PostRowHandler;
import com.ybj.file.parse.core.post.may.CacheHandler;
import com.ybj.file.parse.core.post.may.ExcludeFiltHandler;
import com.ybj.file.parse.core.post.may.MapWrapHandler;
import com.ybj.file.parse.core.post.may.NormalPrinter;
import com.ybj.file.parse.core.post.must.MultiCellsValidatorHandler;
import com.ybj.file.parse.core.post.must.SameTitleMergeHandler;
import com.ybj.file.parse.core.post.must.TypeValidateHandler;
import com.ybj.file.parse.message.ParseContext;

public class PostHandleChain extends TeminationPostRowHandler {

    private FileParseExtractor startHandler;

    public PostHandleChain(FileParseExtractor startHandler) {
        this.startHandler = startHandler;
    }

    public static PostHandleChain getPrinterChain() {
        NormalPrinter np = new NormalPrinter();
        return getBuildinChain(np);
    }

    public static PostHandleChain getBuildinChain(
            Class<? extends TeminationPostRowHandler> teminationPostRowHandlerClazz)
            throws InstantiationException, IllegalAccessException {
        TeminationPostRowHandler teminationHandler = teminationPostRowHandlerClazz.newInstance();
        return getBuildinChain(teminationHandler);
    }

    public static PostHandleChain getBuildinChain(TeminationPostRowHandler teminationHandler) {
        MultiCellsValidatorHandler multiValidator = new MultiCellsValidatorHandler(teminationHandler);
        MapWrapHandler mwh = new MapWrapHandler(multiValidator);
        ExcludeFiltHandler efh = new ExcludeFiltHandler(mwh);
        SameTitleMergeHandler stmh = new SameTitleMergeHandler(efh);
        // TypeConvertHandler tch = new TypeConvertHandler(stmh);
        TypeValidateHandler cellValidator = new TypeValidateHandler(stmh);
        FileParseExtractor extrator = new FileParseExtractor(cellValidator);
        return new PostHandleChain(extrator);
    }

    public static PostHandleChain getXmlCacheChain() {
        CacheHandler cache = new CacheHandler();
        FileParseExtractor extrator = new FileParseExtractor(cache);
        return new PostHandleChain(extrator);
    }

    public void tail(PostRowHandler postRowHandler) {
        PostRowHandler tail = this.startHandler;
        PostRowHandler pre = tail;
        while ((tail = this.startHandler.next()) != null) {
            pre = tail;
        }
        pre.next(postRowHandler);
    }

    public CacheHandler getCacheHandler() {
        PostRowHandler prHandler = null;
        while ((prHandler = this.startHandler.next()) != null) {
            if (prHandler instanceof CacheHandler) {
                return (CacheHandler) prHandler;
            }
        }
        return null;
    }

    @Override
    public void processOne(ParsedRow parsedRow, ParseContext parseContext) throws RowHandleException {
        startHandler.processOne(parsedRow, parseContext);
    }

    public ParseStartHandler getStartHandler() {
        return this.startHandler;
    }
}
