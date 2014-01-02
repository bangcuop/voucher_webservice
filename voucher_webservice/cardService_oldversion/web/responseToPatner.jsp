<%-- 
    Document   : responseToPartner
    Created on : Jan 10, 2012, 5:27:32 PM
    Author     : zannami
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang thai giao dich</title>
    </head>
<body>
        <table align="center" >
            <tr>
                <td><core:out value="${responseToPartner}" /></td>
             </tr>
        </table>
</body>
</html>
