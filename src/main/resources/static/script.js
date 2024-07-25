document.getElementById('createRuleForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const rule = document.getElementById('createRule').value;

    fetch('http://localhost:8080/rules/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/text'
        },
        body: rule
    })
    .then(response => {
        console.log('Create Rule response:', response);
        return response.json();
    })
    .then(data => {
        console.log('Create Rule data:', data);
        document.getElementById('result').innerHTML = `<p>${JSON.stringify({data})}</p>`;
    })
    .catch(error => console.error('Error:', error));
});

document.getElementById('combineRuleForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const rule1 = document.getElementById('combineRule1').value;

    fetch('http://localhost:8080/rules/combine', {
        method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: rule1
    })
    .then(response => {
        console.log('Combine Rule response:', response);
        return response.json();
    })
    .then(data => {
        console.log('Combine Rule data:', data);
        document.getElementById('result').innerHTML += `<p>${JSON.stringify({data})}</p>`;
    })
    .catch(error => console.error('Error:', error));
});

document.getElementById('evaluateRuleForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const input = document.getElementById('evaluateRule').value;

    fetch('http://localhost:8080/rules/evaluate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: input
    })
    .then(response => {
        console.log('Evaluate Rule response:', response);
        return response.json();
    })
    .then(data => {
        console.log('Evaluate Rule data:', data);
        document.getElementById('result').innerHTML += `<p>${data.result}</p>`;
    })
    .catch(error => console.error('Error:', error));
});
