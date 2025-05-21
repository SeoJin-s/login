<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	    $('#addBtn').click(function() {
	        // name 길이 확인
	        if ($('#name').val().length < 4) {
	            alert('name은 4자 이상이어야 합니다.');
	            return;
	        }
	     	// age 숫자 및 범위 확인
	        let ageVal = parseInt($('#age').val());
	        if (isNaN(ageVal) || ageVal < 0 || ageVal > 200) {
	            alert('나이는 숫자이며 0~200 사이여야 합니다.');
	            return;
	        }

	        // 폼 제출
	        $('#addForm').submit();
	    });
	});
</script>
</head>
<body>
	<h1>addSample</h1>
	<span>${errMsg}</span>
	<form id="addForm" method="post" action="/addSample">
		<div>
			NAME : <input type="text" id="name" name="name"> 
			<span style="color:red">${nameErrMsg}</span>
		</div>
	
		<div>
			AGE : <input type="number" id="age" name="age" min="0" max="200">
			<span style="color:red">${ageErrMsg}</span>
		</div>
		
		<div>
			<!-- 자바스크립트 유효성 검사 쓸 거면 button / 아니면 submit -->
			<button type="button" id="addBtn">입력</button>
		</div>
	</form>
</body>
</html>