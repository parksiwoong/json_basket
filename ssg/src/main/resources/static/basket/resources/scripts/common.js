function loadBaskets() {
    function callback(response) {
        let tbody = window.document.body.querySelector('tbody');
        let json = JSON.parse(response);
        let result = json["result"];
        if (result === 'not_allowed') {
            let tr = window.document.createElement('tr');
            let td = window.document.createElement('td');
            td.setAttribute('colspan', '6');
            td.style.textAlign = 'center';
            td.innerText = '로그인 해주세요.';
            tr.append(td);
            tbody.append(tr);
        } else if (result === 'success') {
            let baskets = json['baskets'];
            let sum = 0;
            for (let i = 0; i < baskets.length; i++) {
                let rowSum = parseInt(baskets[i]['itemPrice']) * parseInt(baskets[i]['count']) + parseInt(baskets[i]['colorVariation']) + parseInt(baskets[i]['sizeVariation']);
                let tr = window.document.createElement('tr');
                let tdDate = window.document.createElement('td');
                let tdName = window.document.createElement('td');
                let tdColor = window.document.createElement('td');
                let tdSize = window.document.createElement('td');
                let tdCount = window.document.createElement('td');
                let tdTotal = window.document.createElement('td');
                tdDate.innerText = baskets[i]['dateTime'];
                tdName.innerText = baskets[i]['itemName'];
                tdColor.innerText = baskets[i]['colorName'];
                tdSize.innerText = baskets[i]['sizeName'];
                tdCount.innerText = baskets[i]['count'];
                tdTotal.innerText =  rowSum.toLocaleString();
                tr.append(tdDate);
                tr.append(tdName);
                tr.append(tdColor);
                tr.append(tdSize);
                tr.append(tdCount);
                tr.append(tdTotal);
                tbody.append(tr);
                sum += rowSum;
            }
            let total = window.document.body.querySelector('tfoot > tr > td')
            total.innerText = sum.toLocaleString();
        }
    }

    function fallback() {

    }

    Ajax.request('POST', '/apis/basket/get', callback, fallback);
}

loadBaskets();