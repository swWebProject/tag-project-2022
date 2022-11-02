function showPassword(event) {
    if (event.target.checked) {
        document.getElementById('login_pw').setAttribute('type', 'text');
    } else {
        document.getElementById('login_pw').setAttribute('type', 'password');
    }
}