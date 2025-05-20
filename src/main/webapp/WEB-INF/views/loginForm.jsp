<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>로그인</title>
</head>
<body>
  <h2>로그인</h2>
  
  <c:if test="${not empty msg}">
  <div style="color: green; margin-bottom: 10px;">
    ${msg}
  </div>
</c:if>

  <form action="login" method="post">
  <div>
    <label>아이디:</label>
    <input type="text" name="memberId" required>
  </div>
  <div>
    <label>비밀번호:</label>
    <input type="password" name="memberPw" required>
  </div>
  <div>
    <button type="submit">로그인</button>
  </div>
  <div>
    <a href="${pageContext.request.contextPath}/findPwForm">비밀번호 찾기</a>
  </div>
</form>

</body>
</html>
