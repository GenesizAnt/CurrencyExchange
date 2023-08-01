package currencyservlet;

import com.example.controller.QueriesControl;
import com.example.entity.ErrorQuery;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Util.*;

@WebServlet("/currency/*")
public class GetCurrencyServlet extends HttpServlet {

    private QueriesControl queriesControl = new QueriesControl();
    private ErrorQuery errorQuery;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isCorrectCodeCurrency(request)) {
            String currencyCode = getCodeFromURL(request);
            queriesControl.getCurrency(currencyCode, response);
        } else {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Incorrect request - 400");
            getJsonResponse(errorQuery, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
