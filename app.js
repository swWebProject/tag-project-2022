/* home script */

/* 변수 선언부 ---------------------------------------------------------- */

/* 변수 선언 */
// let movieInfo = { // 영화 정보 객체
//     movieNm: "", // 영화이름
//     movieOpenDt: "", // 개봉일
//     audiAcc: "", // 누적 관객수
//     movieCd: "", // 영화코드
//     movieLink: "", // 영화 상세정보 링크
//     movieImg: "", // 썸네일 링크
//     moviePubDt: "", //제작년도
//     movieDirector: "", // 감독
//     movieActor: [], // 배우
//     movieUserRating: "", // 평점
//     movieNations: "" // 제작 국가
// }

// let movie = []; // 영화 정보 객체를 담을 배열

// /* 영화 검색 날짜 */
// let dateObj = new Date();
// let movieSearch;
// if (dateObj.getDate() < 10) {
//     movieSearch = String(dateObj.getFullYear()) + String(dateObj.getMonth() + 1) + "0" + String(dateObj.getDate()) - 1;
// } else {
//     movieSearch = String(dateObj.getFullYear()) + String(dateObj.getMonth() + 1) + String(dateObj.getDate() - 1);
// }

// let i = 0;

// /* api 선언부 ---------------------------------------------------------- */

// /* 영화진흥위원회 API */

// // url : http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json

// let api_key_movie = "key=22f10dd3863febae7e67b8df14051d8b"; // API 키
// let targetDt = "&targetDt=" + movieSearch;

// const movie_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?" + api_key_movie + targetDt;
// const movie_detail_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?" + api_key_movie + "&movieCd=";

// /** 영화진흥위원회 API fetch 함수 */
// fetch(movie_url)
//     .then(response => response.json())
//     .then(function (json) {
//         console.log(`${movieSearch} 일자의 데이터입니다.`);
//         let movieJson = json.boxOfficeResult.dailyBoxOfficeList;

//         for (let i = 0; i < movieJson.length; i++) {

//             movie[i] = movieInfo; // 배열에 영화 정보를 담은 객체 넣기
//             movie[i].movieNm = movieJson[i].movieNm // 영화 이름
//             movie[i].movieOpenDt = movieJson[i].openDt; // 개봉일
//             movie[i].audiAcc = movieJson[i].audiAcc; // 누적관객수
//             movie[i].movieCd = movieJson[i].movieCd; // 영화 코드

//             console.log(movie[i]);
//             console.log(`${i + 1}: ${movie[i].movieNm}, ${movie[i].movieOpenDt}, ${movie[i].audiAcc}명`);

//         }
//         console.log(json)



//     })
//     .then(function (msg) {
//         /** 영화진흥위원회 영화상세정보 API fetch 함수 */
//         let url = movie_detail_url + movie[i].movieCd;
//         fetch(url)
//             .then(res => res.json())
//             .then(function (res) {
//                 alert();
//                 console.log(res)
//             });
//     });



// /* 네이버 영화 검색 API */

// let api_key_naver = "62UzBxjz8nqOli5tiZjO"; // 네이버 API 키
// let api_key_naver_secret = "QgJu2vI55p"; // 네이버 시크릿 API 키

// /** 네이버 영화검색 API Ajax 호출 함수 */
// function callAjaxNaver(movieNm, moviePubDt) {
//     $.ajax({
//         method: "GET",   // HTTP 요청 메소드(GET, POST 등)
//         url: "https://cors-anywhere.herokuapp.com/https://openapi.naver.com/v1/search/movie.json", // 클라이언트가 HTTP 요청을 보낼 서버의 URL 주소
//         data: { query: movieNm, yearto: moviePubDt },
//         headers: {
//             'X-Naver-Client-Id': api_key_naver,
//             'X-Naver-Client-Secret': api_key_naver_secret
//         }
//     })
//         // HTTP 요청이 성공하면 요청한 데이터가 done() 메소드로 전달됨.
//         .done(function (json) {
//             console.log(json);
//         });
// }


/** 로그인 시만 사용할 수 있는 서비스 */
function service() {
    let signIn = "${signIn}";
    if (signIn == "") {
        alert("서비스 페이지는 로그인 후 사용하실 수 있습니다.");
        location.href = "/Zoo/login/login.html";
    } else {
        location.href = "/Zoo/login.login.html";
    }
}

/** 로그인 시 html 변경 */
function login() {
    let signIn = "${signIn}";
    if (signIn == "") {
        let center = document.getElementById('login_log');
        center.innerHTML = "마이페이지";
        center.href = "/Zoo/mypage/mypage.html";
        let right = document.getElementById('login_join');
        right.innerHTML = "로그아웃";
        right.href = "";
    } else {
        location.href = "/Zoo/login.login.html";
    }
}