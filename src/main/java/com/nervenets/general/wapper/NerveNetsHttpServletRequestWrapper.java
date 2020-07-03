package com.nervenets.general.wapper;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 解决request.getInputStream()只能读一次，在ExceptionHandler里面读取不到的情况
 * <p>
 * 2020/6/23 17:46 created by Joe
 **/
public class NerveNetsHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] bodyCopier;

    public NerveNetsHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        bodyCopier = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStreamCopier(bodyCopier);
    }

    public byte[] getCopy() {
        return this.bodyCopier;
    }

    public String getBody() {
        return new String(this.bodyCopier, StandardCharsets.UTF_8);
    }

    private static class ServletInputStreamCopier extends ServletInputStream {
        private final ByteArrayInputStream bais;

        public ServletInputStreamCopier(byte[] in) {
            this.bais = new ByteArrayInputStream(in);
        }

        @Override
        public boolean isFinished() {
            return bais.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public int read() {
            return this.bais.read();
        }

    }
}
