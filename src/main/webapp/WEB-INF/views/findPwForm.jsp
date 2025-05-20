<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>비밀번호 찾기</title>
</head>
<body>
  <h2>비밀번호 찾기</h2>
  <form action="findPw" method="post">
    <div>
      <label>아이디:</label>
      <input type="text" name="id" required>
    </div>
    <div>
      <label>이메일:</label>
      <input type="email" name="email" required>
    </div>
    <div>
      <button type="submit">임시 비밀번호 발급</button>
    </div>
  </form>
</body>
</html>
