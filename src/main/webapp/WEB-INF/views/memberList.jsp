<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<style>
    table {
        border-collapse: collapse;
        width: 100%;
    }
    th, td {
        border: 1px solid #000;
        padding: 8px;
        text-align: left;
    }
    th {
        background-color: #f2f2f2;
    }
</style>
</head>
<body>
	<h1>회원목록</h1>
	
	<table>
		<thead>
  <tr>
    <th>ID</th>
    <th>EMAIL</th>
    <th>상태</th> <!-- ✅ 이 한 칸에만 다 들어가야 함 -->
  </tr>
</thead>
<tbody>
  <c:forEach var="member" items="${memberList}">
    <tr>
      <td>${member.id}</td>
      <td>${member.email}</td>
      <td>
        <form action="toggleActive" method="post" style="display: flex; align-items: center; gap: 8px;">
          <input type="hidden" name="id" value="${member.id}" />

          <!-- 상태 라벨 -->
          <span style="
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 0.8rem;
            font-weight: bold;
            background-color: ${member.active == 'ON' ? '#d1e7dd' : '#f8d7da'};
            color: ${member.active == 'ON' ? '#0f5132' : '#842029'};
            border: 1px solid ${member.active == 'ON' ? '#badbcc' : '#f5c2c7'};
          ">
            ${member.active}
          </span>

          <!-- 상태 변경 버튼 -->
          <button type="submit"
                  style="padding: 5px 10px;
                         font-size: 0.8rem;
                         background-color: ${member.active == 'ON' ? '#f8d7da' : '#d1e7dd'};
                         border: 1px solid ${member.active == 'ON' ? '#f5c2c7' : '#badbcc'};
                         color: ${member.active == 'ON' ? '#842029' : '#0f5132'};
                         border-radius: 6px;
                         font-weight: 600;
                         cursor: pointer;">
            ${member.active == 'ON' ? '비활성화' : '활성화'}
          </button>
        </form>
      </td>
    </tr>
  </c:forEach>
</tbody>

	</table>

	<!-- 페이징 -->
	<div class="text-center mt-4" style="margin-top: 20px;">
	  <c:if test="${startPage > 1}">
	    <a href="memberList?currentPage=${startPage - 1}">이전</a>
	  </c:if>

	  <c:forEach var="i" begin="${startPage}" end="${endPage}">
	    <a href="memberList?currentPage=${i}" 
	       style="margin: 0 4px; 
	              font-weight: ${i == currentPage ? 'bold' : 'normal'};
	              color: ${i == currentPage ? 'black' : 'blue'};">
	      ${i}
	    </a>
	  </c:forEach>

	  <c:if test="${endPage < lastPage}">
	    <a href="memberList?currentPage=${endPage + 1}">다음</a>
	  </c:if>
	</div>
</body>
</html>
