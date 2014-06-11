package jp.tuyano;
 
import java.io.*;
import java.util.*;
 
import javax.jdo.*;
import javax.servlet.http.*;
 
@SuppressWarnings("serial")
public class FindDataServlet extends HttpServlet {
    public void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {
        PersistenceManagerFactory factory = PMF.get();
        PersistenceManager manager = factory.getPersistenceManager();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        req.setCharacterEncoding("utf-8");
        String param1 = req.getParameter("find");
        PrintWriter out = resp.getWriter();
        List<LinkData> list = null;
        Query query = manager.newQuery(LinkData.class);
        query.setFilter("title == findTitle");
        query.setOrdering("datetime desc");
        query.declareParameters("String findTitle");
        try {
            list = (List<LinkData>)query.execute(param1);
        } catch(JDOObjectNotFoundException e){}
        String res = "[";
        if (list != null){
            for(LinkData data:list){
                res += "{id:" + data.getId() + ",url:'" + data.getUrl() + "',title:'" +
                    data.getTitle() + "',date:'" + data.getDatetime() +
                    "',comment:'" + data.getComment() + "'},";
            }
        }
        res += "]";
        out.println(res);
        manager.close();
    }
}