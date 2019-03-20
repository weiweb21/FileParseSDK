package com.yanbenjun.file.parse.core;

import com.yanbenjun.file.model.parse.ParsedRow;
import com.yanbenjun.file.parse.core.exception.RowHandleException;
import com.yanbenjun.file.parse.core.post.FileParseExtractor;
import com.yanbenjun.file.parse.core.post.ParseStartHandler;
import com.yanbenjun.file.parse.core.post.TeminationPostRowHandler;
import com.yanbenjun.file.parse.core.post.infs.PostRowHandler;
import com.yanbenjun.file.parse.core.post.may.CacheHandler;
import com.yanbenjun.file.parse.core.post.may.ExcludeFiltHandler;
import com.yanbenjun.file.parse.core.post.may.MapWrapHandler;
import com.yanbenjun.file.parse.core.post.may.NormalPrinter;
import com.yanbenjun.file.parse.core.post.must.SameTitleMergeHandler;
import com.yanbenjun.file.parse.core.post.must.TypeConvertHandler;
import com.yanbenjun.file.parse.message.ParseContext;

public class PostHandleChain extends TeminationPostRowHandler
{
    private FileParseExtractor startHandler;

    public PostHandleChain(FileParseExtractor startHandler)
    {
        this.startHandler = startHandler;
    }

    public static PostHandleChain getPrinterChain()
    {
        NormalPrinter np = new NormalPrinter();
        MapWrapHandler mwh = new MapWrapHandler();
        ExcludeFiltHandler efh = new ExcludeFiltHandler();
        SameTitleMergeHandler stmh = new SameTitleMergeHandler();
        stmh.next(efh).next(mwh).next(np);
        TypeConvertHandler tch = new TypeConvertHandler(stmh);

        FileParseExtractor extrator = new FileParseExtractor(tch);
        return new PostHandleChain(extrator);
    }

    public static PostHandleChain getBuildinChain(
            Class<? extends TeminationPostRowHandler> TeminationPostRowHandlerClazz)
            throws InstantiationException, IllegalAccessException
    {
        TeminationPostRowHandler teminationHandler;
        teminationHandler = TeminationPostRowHandlerClazz.newInstance();
        MapWrapHandler mwh = new MapWrapHandler();
        ExcludeFiltHandler efh = new ExcludeFiltHandler();
        SameTitleMergeHandler stmh = new SameTitleMergeHandler();
        stmh.next(efh).next(mwh).next(teminationHandler);
        TypeConvertHandler tch = new TypeConvertHandler(stmh);
        FileParseExtractor extrator = new FileParseExtractor(tch);
        return new PostHandleChain(extrator);
    }

    public static PostHandleChain getXmlCacheChain()
    {
        CacheHandler cache = new CacheHandler();
        FileParseExtractor extrator = new FileParseExtractor(cache);
        return new PostHandleChain(extrator);
    }

    public void tail(PostRowHandler postRowHandler)
    {
        PostRowHandler tail = this.startHandler;
        PostRowHandler pre = tail;
        while ((tail = this.startHandler.next()) != null)
        {
            pre = tail;
        }
        pre.next(postRowHandler);
    }

    public CacheHandler getCacheHandler()
    {
        PostRowHandler prHandler = null;
        while ((prHandler = this.startHandler.next()) != null)
        {
            if (prHandler instanceof CacheHandler)
            {
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
