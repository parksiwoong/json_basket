<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>장바구니</title>
    <script defer src="resources/scripts/ajax.js"></script>
    <script defer src="basket/resources/scripts/common.js"></script>
</head>
<body>
<table>
    <thead>
        <tr>
            <th>추가 날짜</th>
            <th>상품</th>
            <th>색상</th>
            <th>크기</th>
            <th>갯수</th>
            <th>총액</th>
        </tr>
    </thead>
    <tbody></tbody>
    <tfoot>
        <tr>
            <th colspan="5">총합</th>
            <td>0</td>
        </tr>
    </tfoot>
</table>
</body>
</html>