<%--
  Created by IntelliJ IDEA.
  User: Sibastus
  Date: 05.10.2018
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_menu.jsp"></jsp:include>

<h3>Product List</h3>

<p style="color: red;">${errorString}</p>

<table border="1" cellpadding="5" cellspacing="1" >
    <tr>
        <th>Image</th>
        <th>ID</th>
        <th>Name</th>
        <th>Page Count</th>
        <th>ISBN</th>
        <th>Genre</th>
        <th>Author</th>
        <th>Publish year</th>
        <th>Publisher</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${productList}" var="book" >
        <tr>
            <td><img width="200" height="180" src="/image"></td>
            <td>${book.id}</td>
            <td>${book.name}</td>
            <td>${book.pageCount}</td>
            <td>${book.isbn}</td>
            <td>${book.genre}</td>
            <td>${book.author}</td>
            <td>${book.publishDate}</td>
            <td>${book.publisher}</td>
            <td>
                <a href="editProduct?id=${book.id}">Edit</a>
            </td>
            <td>
                <a href="deleteProduct?id=${book.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="createProduct" >Create Product</a>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>