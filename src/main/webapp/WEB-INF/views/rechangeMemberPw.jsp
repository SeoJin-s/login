<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>비밀번호 재설정</title>
</head>
<body>
  <h2>비밀번호 재설정</h2>

  <!-- 메시지 출력 -->
  <c:if test="${not empty msg}">
    <div style="color: red; margin-bottom: 10px;">
      ${msg}
    </div>
  </c:if>

  <form action="/rechangeMemberPw" method="post">
    <input type="hidden" name="memberId" value="${param.memberId}" />

    <div>
      <label>임시 비밀번호:</label>
      <input type="password" name="memberPw" required />
    </div>
    <div>
      <label>새 비밀번호:</label>
      <input type="password" name="newMemberPw" required />
    </div>
    <div>
      <button type="submit">비밀번호 변경</button>
    </div>
  </form>
</body>
</html>
