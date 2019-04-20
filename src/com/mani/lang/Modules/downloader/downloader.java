package com.mani.lang.Modules.downloader;

import com.mani.lang.Interpreter;
import com.mani.lang.ManiCallable;
import com.mani.lang.ManiFunction;
import com.mani.lang.Modules.Module;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class downloader implements Module {
    @Override
    public void init(Interpreter interpreter) {
        interpreter.addSTD("download", new ManiCallable() {
            @Override
            public int arity() {
                return -1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                if (!(arguments.size() >= 2)) {
                    System.err.println("Must be atleast 2 arguments in downloader.");
                    return null;
                }
                final String downloadURL = (String) arguments.get(0);
                final String path = (String) arguments.get(1);
                final ManiFunction callback;
                final double contentLength;
                if (arguments.size() == 3) {
                    callback = (ManiFunction) arguments.get(2);
                    contentLength = getContentLength(downloadURL);
                } else {
                    callback = null;
                    contentLength = -1;
                }
                final int bufferSize = (arguments.size() == 4) ? Math.max(1024, Integer.valueOf((Integer) arguments.get(3))) : 16384;
                final boolean calculateProgressEnabled = contentLength > 0 && callback != null;

                if (calculateProgressEnabled) {
                    List<Object> db = new ArrayList<>();
                    db.add(new Double(0));
                    db.add(new Double(0));
                    db.add(contentLength);
                    callback.call(interpreter, db);
                }

                try (InputStream is = new URL(downloadURL).openStream();
                     OutputStream os = new FileOutputStream(new File(path))) {
                    int downloaded = 0;
                    final byte[] buffer = new byte[bufferSize];
                    int read;

                    while ((read = is.read(buffer, 0, bufferSize)) != -1) {
                        os.write(buffer, 0, read);
                        downloaded += read;
                        if (calculateProgressEnabled) {
                            final int percent = (int) (downloaded / ((double) contentLength) * 100.0);
                            List<Object> db = new ArrayList<>();
                            db.add(new Double(percent));
                            db.add(new Double(downloaded));
                            db.add(contentLength);
                            callback.call(interpreter, db);
                        }
                    }
                } catch (IOException ioe) {
                    return new Double(0);
                } finally {
                    if (callback != null) {
                        List<Object> db = new ArrayList<>();
                        db.add(new Double(100));
                        db.add(contentLength);
                        db.add(contentLength);
                        callback.call(interpreter, db);
                    }
                }

                return null;
            }
        });
    }

    private static int getContentLength(String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            return connection.getContentLength();
        } catch (IOException ioe) {
            return -1;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public boolean hasExtensions() {
        return false;
    }

    @Override
    public Object extensions() {
        return null;
    }
}
