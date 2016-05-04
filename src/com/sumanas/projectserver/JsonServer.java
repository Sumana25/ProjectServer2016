package com.sumanas.projectserver;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JsonServer {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8080;
    private static final int BACKLOG = 1;

    private static final String HEADER_ALLOW = "Allow";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private static final int STATUS_OK = 200;
    private static final int STATUS_METHOD_NOT_ALLOWED = 405;

    private static final int NO_RESPONSE_LENGTH = -1;

    private static final String METHOD_GET = "GET";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String ALLOWED_METHODS = METHOD_GET + "," + METHOD_OPTIONS;

    public static void main(final String... args) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    new ServerDiscoverer().respondToDiscovery();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        final HttpServer server = HttpServer.create(new InetSocketAddress(PORT), BACKLOG);
        server.createContext("/do_action", he -> {
            try {
                final Headers headers = he.getResponseHeaders();
                final String requestMethod = he.getRequestMethod().toUpperCase();
                switch (requestMethod) {
                    case METHOD_GET:
                        final Map<String, List<String>> requestParameters = getRequestParameters(he.getRequestURI());

                        // do something with the request parameters
                        final String responseBody = handleRequest(requestParameters);
                        headers.set(HEADER_CONTENT_TYPE, String.format("application/json; charset=%s", CHARSET));
                        final byte[] rawResponseBody = responseBody.getBytes(CHARSET);
                        he.sendResponseHeaders(STATUS_OK, rawResponseBody.length);
                        he.getResponseBody().write(rawResponseBody);

                        break;
                    case METHOD_OPTIONS:
                        headers.set(HEADER_ALLOW, ALLOWED_METHODS);
                        he.sendResponseHeaders(STATUS_OK, NO_RESPONSE_LENGTH);
                        break;
                    default:
                        headers.set(HEADER_ALLOW, ALLOWED_METHODS);
                        he.sendResponseHeaders(STATUS_METHOD_NOT_ALLOWED, NO_RESPONSE_LENGTH);
                        break;
                }
            } finally {
                he.close();
            }
        });
        server.start();


    }

    private static Map<String, List<String>> getRequestParameters(final URI requestUri) {
        final Map<String, List<String>> requestParameters = new LinkedHashMap<>();
        final String requestQuery = requestUri.getRawQuery();
        if (requestQuery != null) {
            final String[] rawRequestParameters = requestQuery.split("[&;]", -1);
            for (final String rawRequestParameter : rawRequestParameters) {
                final String[] requestParameter = rawRequestParameter.split("=", 2);
                final String requestParameterName = decodeUrlComponent(requestParameter[0]);
                requestParameters.putIfAbsent(requestParameterName, new ArrayList<>());
                final String requestParameterValue = requestParameter.length > 1 ? decodeUrlComponent(requestParameter[1]) : null;
                requestParameters.get(requestParameterName).add(requestParameterValue);
            }
        }
        return requestParameters;
    }

    private static String decodeUrlComponent(final String urlComponent) {
        try {
            return URLDecoder.decode(urlComponent, CHARSET.name());
        } catch (final UnsupportedEncodingException ex) {
            throw new InternalError(ex);
        }
    }

    private static String handleRequest(Map<String, List<String>> request) {


            switch (request.get("action"). get(0)) {
                case "open_notepad" :
                    try {
                        Runtime.getRuntime().exec("notepad");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }

                case "open_mp3" :
                    try {
                        Runtime.getRuntime().exec("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe \"H:\\Music\\Kannada\\Kannada Naadina.mp3\"");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }
                case "open_friends" :
                    try {
                        Runtime.getRuntime().exec("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe \"F:\\Friends\\Friends Season 4\\Friends - [4x12] - The One with the Embryos + Audio Commentary.mkv\"");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }
                case "type":
                    try {
                        Runtime.getRuntime().exec("./scripts/type.exe \""+request.get("arg"). get(0)+'"');
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }
                case "delete_word":
                    try {
                        Runtime.getRuntime().exec("./scripts/delete_word.exe");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }
                case "undo":
                    try {
                        Runtime.getRuntime().exec("./scripts/undo.exe");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }
                case "new_line":
                case "newline":
                case "next_line":
                case "nextline":
                    try {
                        Runtime.getRuntime().exec("./scripts/enter.exe");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }
                case "close_window":
                    try {
                        Runtime.getRuntime().exec("./scripts/close_window.exe");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }
                case "switch_window":
                    try {
                        Runtime.getRuntime().exec("./scripts/switch_window.exe");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }
                case "time" :
                    try {
                        Runtime.getRuntime().exec("timedate.cpl");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }

                case "calculator" :
                    try {
                        Runtime.getRuntime().exec("calc.exe");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }
                case "explorer" :
                    try {
                        Runtime.getRuntime().exec("explorer.exe");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }

                case "cmd" :
                    try {
                        Runtime.getRuntime().exec("cmd.exe");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }

                case "photos" :
                    try {
                        Runtime.getRuntime().exec("./scripts/picasa.exe");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }
                case "videos":
                    System.out.println(showVideoList());
                    return showVideoList();
                case "play_video" :
                    try {
                        Runtime.getRuntime().exec("./scripts/play_video.exe \""+request.get("arg"). get(0)+'"');
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }

                case "play" :
                case "pause":
                    try {
                        Runtime.getRuntime().exec("./scripts/play_pause.exe");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }

                case "volup":
                    try {
                        Runtime.getRuntime().exec("./scripts/volup.exe");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }
                case "voldown":
                    try {
                        Runtime.getRuntime().exec("./scripts/voldown.exe");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        return "OK";
                    }






                default:return "NOT_OK";



            }

//        return "OK";
    }

    public static String showVideoList() {
        String userprofile = System.getenv("USERPROFILE");
        File folder = new File(userprofile+"\\Videos");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String>fileNameList = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                fileNameList.add(listOfFiles[i].getName());

            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        Gson gson = new Gson();
        return gson.toJson(fileNameList).toString();
    }

}

