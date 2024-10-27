package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CalculatorServletTest {

    private CalculatorServlet calculatorServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    public void setUp() {
        calculatorServlet = new CalculatorServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void testAddition() throws ServletException, IOException {
        when(request.getParameter("num1")).thenReturn("5");
        when(request.getParameter("num2")).thenReturn("3");
        when(request.getParameter("operation")).thenReturn("add");
        when(request.getRequestDispatcher("result.jsp")).thenReturn(requestDispatcher);

        calculatorServlet.doPost(request, response);

        verify(request).setAttribute("result", 8.0);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testSubtraction() throws ServletException, IOException {
        when(request.getParameter("num1")).thenReturn("10");
        when(request.getParameter("num2")).thenReturn("4");
        when(request.getParameter("operation")).thenReturn("subtract");
        when(request.getRequestDispatcher("result.jsp")).thenReturn(requestDispatcher);

        calculatorServlet.doPost(request, response);

        verify(request).setAttribute("result", 6.0);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testMultiplication() throws ServletException, IOException {
        when(request.getParameter("num1")).thenReturn("6");
        when(request.getParameter("num2")).thenReturn("7");
        when(request.getParameter("operation")).thenReturn("multiply");
        when(request.getRequestDispatcher("result.jsp")).thenReturn(requestDispatcher);

        calculatorServlet.doPost(request, response);

        verify(request).setAttribute("result", 42.0);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDivision() throws ServletException, IOException {
        when(request.getParameter("num1")).thenReturn("20");
        when(request.getParameter("num2")).thenReturn("4");
        when(request.getParameter("operation")).thenReturn("divide");
        when(request.getRequestDispatcher("result.jsp")).thenReturn(requestDispatcher);

        calculatorServlet.doPost(request, response);

        verify(request).setAttribute("result", 5.0);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDivisionByZero() throws ServletException, IOException {
        when(request.getParameter("num1")).thenReturn("10");
        when(request.getParameter("num2")).thenReturn("0");
        when(request.getParameter("operation")).thenReturn("divide");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        calculatorServlet.doPost(request, response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("Error: Cannot divide by zero"));
    }

    @Test
    public void testInvalidNumberFormat() throws ServletException, IOException {
        when(request.getParameter("num1")).thenReturn("invalid");
        when(request.getParameter("num2")).thenReturn("2");
        when(request.getParameter("operation")).thenReturn("add");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        calculatorServlet.doPost(request, response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("Error: For input string"));
    }
}

