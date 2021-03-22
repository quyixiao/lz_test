package com.admin.crawler.exception;

public interface SimpleStackTracePrintable {
    String LINE_SEPARATOR = System.getProperty("line.separator");
    int MAX_STACK = 3;

    StackTraceElement[] allStackTrace();

    default String simpleStackTrace() {
        StringBuilder temp = new StringBuilder(this.toString());
        StackTraceElement[] steArray = allStackTrace();
        if (steArray != null) {
            int max = steArray.length < MAX_STACK ? steArray.length : MAX_STACK;
            for (int i = 0; i < max; i++) {
                temp.append(LINE_SEPARATOR);
                temp.append(steArray[i].toString());
            }
        }
        return temp.toString();
    }
}
