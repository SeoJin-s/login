<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>비밀번호 변경</title>
    <style>
        .error { color: red; }
    </style>
</head>
<body>
    <h2>비밀번호 변경</h2>

    <!-- Flash 메시지 (이전에 사용한 비밀번호 등) -->
    <c:if test="${not empty msg}">
        <div class="error">${msg}</div>
    </c:if>

    <!-- 전역 에러 메시지 (기존 로직 유지) -->
    <c:if test="${not empty errMsg}">
        <div class="error">${errMsg}</div>
    </c:if>

    <form method="post" action="/changePw">
    	<input type="hidden" name="id" value="${memberId}" />
        <div>
            현재 비밀번호: <input type="password" name="currentPw" />
            <span class="error">${currentPwErrorMsg}</span>
        </div>
        <div>
            새 비밀번호: <input type="password" name="newPw" />
            <span class="error">${newPwErrorMsg}</span>
        </div>
        <div>
            새 비밀번호 확인: <input type="password" name="confirmPw" />
            <span class="error">${confirmPwErrorMsg}</span>
        </div>

        <div>
            <button type="submit">변경하기</button>
        </div>
    </form>
</body>
</html>
