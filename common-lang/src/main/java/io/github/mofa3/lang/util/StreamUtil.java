/*
 * Copyright 2020 lujing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.mofa3.lang.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * io流读取
 *
 * @author lumoere
 * @version $Id: StreamUtil.java, v 0.1 2018-09-26 下午7:24 Exp $
 */
@SuppressWarnings("unused")
public class StreamUtil {
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    public static void io(InputStream in, OutputStream out) throws IOException {
        io(in, out, -1);
    }

    public static void io(InputStream in, OutputStream out, int bufferSize) throws IOException {
        if (bufferSize == -1) {
            bufferSize = DEFAULT_BUFFER_SIZE;
        }
        byte[] buffer = new byte[bufferSize];
        int amount;
        while ((amount = in.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }
    }

    public static void io(Reader in, Writer out) throws IOException {
        io(in, out, -1);
    }

    public static void io(Reader in, Writer out, int bufferSize) throws IOException {
        if (bufferSize == -1) {
            bufferSize = DEFAULT_BUFFER_SIZE >> 1;
        }
        char[] buffer = new char[bufferSize];
        int amount;
        while ((amount = in.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }
    }

    public static OutputStream synchronizedOutputStream(OutputStream out) {
        return new SynchronizedOutputStream(out);
    }

    public static OutputStream synchronizedOutputStream(OutputStream out, Object lock) {
        return new SynchronizedOutputStream(out, lock);
    }

    public static String readText(InputStream in) throws IOException {
        return readText(in, null, -1);
    }

    public static String readText(InputStream in, String encoding) throws IOException {
        return readText(in, encoding, -1);
    }

    public static String readText(InputStream in, String encoding, int bufferSize)
            throws IOException {
        Reader reader = (encoding == null) ? new InputStreamReader(in) : new InputStreamReader(in,
                encoding);
        return readText(reader, bufferSize);
    }

    public static String readText(Reader reader) throws IOException {
        return readText(reader, -1);
    }

    public static String readText(Reader reader, int bufferSize) throws IOException {
        StringWriter writer = new StringWriter();
        io(reader, writer, bufferSize);
        return writer.toString();
    }

    private static class SynchronizedOutputStream extends OutputStream {
        private OutputStream out;
        private final Object lock;

        SynchronizedOutputStream(OutputStream out) {
            this(out, out);
        }

        SynchronizedOutputStream(OutputStream out, Object lock) {
            this.out = out;
            this.lock = lock;
        }

        @Override
        public void write(int datum) throws IOException {
            synchronized (lock) {
                out.write(datum);
            }
        }

        @Override
        public void write(byte[] data) throws IOException {
            synchronized (lock) {
                out.write(data);
            }
        }

        @Override
        public void write(byte[] data, int offset, int length) throws IOException {
            synchronized (lock) {
                out.write(data, offset, length);
            }
        }

        @Override
        public void flush() throws IOException {
            synchronized (lock) {
                out.flush();
            }
        }

        @Override
        public void close() throws IOException {
            synchronized (lock) {
                out.close();
            }
        }
    }
}
