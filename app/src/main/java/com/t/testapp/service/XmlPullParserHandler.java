package com.t.testapp.service;

import com.t.testapp.model.BookItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlPullParserHandler {
    private static final String TAG_BOOK = "BOOK";
    private List<BookItem> bookItemArrayList = new ArrayList<>();
    String currentTag = null;
    String text = "";
    private BookItem bookItem;

    public XmlPullParserHandler(){

    }
    public List<BookItem> getBookList(){
        return bookItemArrayList;
    }
    public List<BookItem> parse(InputStream is) {
        try {
            XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
            pullParserFactory.setNamespaceAware(true);
            XmlPullParser pullParser = pullParserFactory.newPullParser();
            pullParser.setInput(is, null);
            int eventType = pullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                currentTag = pullParser.getName();
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    bookItemArrayList.clear();
                }
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (currentTag.equalsIgnoreCase(TAG_BOOK)) {
                            bookItem = new BookItem();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = pullParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (currentTag.equalsIgnoreCase(TAG_BOOK)) {
                            bookItemArrayList.add(bookItem);
                        } else if (currentTag.equalsIgnoreCase("ID")) {
                            bookItem.setId(Integer.parseInt(text));
                        } else if (currentTag.equalsIgnoreCase("TITLE")) {
                            bookItem.setTitle(text);
                        } else if (currentTag.equalsIgnoreCase("THUMBNAIL")) {
                            bookItem.setUrl(text);
                        } else if (currentTag.equalsIgnoreCase("NEW")) {
                            bookItem.setIs_new(text);
                        } else if (currentTag.equalsIgnoreCase("THUMB_EXT")) {
                            bookItem.setThumb_ext(text);
                        }
                        break;

                    default:
                        break;
                }
                eventType = pullParser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return bookItemArrayList;
    }

}
