/** 로그인 시만 사용할 수 있는 서비스 */
function service() {
    let signIn = "${signIn}";
    if (signIn == "") {
        alert("서비스 페이지는 로그인 후 사용하실 수 있습니다.");
        location.href = "/Zoo/login/login.html";
    } else {
        location.href = ".html"
    }
}