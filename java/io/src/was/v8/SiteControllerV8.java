package was.v8;

import was.httpserver.HttpResponse;
import was.httpserver.servlet.annotation.Mapping;

public class SiteControllerV8 {

    @Mapping("/")
    public void home(HttpResponse response) {
        response.writeBody("<h1>home</h1>");
        response.writeBody("<ul>");
        response.writeBody("<li><a href='/site1'>site1</li>");
        response.writeBody("<li><a href='/site2'>site2</li>");
        response.writeBody("<li><a href='/search?q=hello'>검색</li>");
        response.writeBody("</ul>");
    }

    @Mapping("/site1")
    public void site1(HttpResponse response) {
        response.writeBody("<h1>site1</h1>");
    }

    @Mapping("/site2")
    public void site2(HttpResponse response) {
        response.writeBody("<h1>site2</h1>");
    }

//    @Mapping("/site2")
//    public void page2(HttpResponse response) {
//        response.writeBody("<h1>site2</h1>");
//    }
}
