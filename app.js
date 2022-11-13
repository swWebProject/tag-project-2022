/* 영화 검색 날짜 */
let dateObj = new Date();
let movieSearch;
if (dateObj.getDate() < 10) {
    movieSearch = String(dateObj.getFullYear()) + String(dateObj.getMonth() + 1) + "0" + String(dateObj.getDate()) - 1;
} else {
    movieSearch = String(dateObj.getFullYear()) + String(dateObj.getMonth() + 1) + String(dateObj.getDate() - 1);
}


/* api 선언부 ---------------------------------------------------------- */

/* 영화진흥위원회 API */

// url : http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json

let api_key_movie = "key=22f10dd3863febae7e67b8df14051d8b"; // API 키
let targetDt = "&targetDt=" + movieSearch;

const movie_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?" + api_key_movie + targetDt;
const movie_detail_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?" + api_key_movie + "&movieCd=";

/** 영화진흥위원회 API fetch 함수 */
function callFetchMovie() {
    fetch(movie_url)
        .then(response => response.json())
        .then(function (json) {
            console.log(`${movieSearch} 일자의 데이터입니다.`);
            let movieJson = json.boxOfficeResult.dailyBoxOfficeList;
            console.log(movieJson)
            for (let i = 0; i < movieJson.length; i++) {
                let plusSection = document.getElementById('section_main_div');

                let new_pTag = document.createElement('p');
                new_pTag.setAttribute('id', 'new_pTag');

                let new_pTag_movieRank = document.createElement('p');
                let new_pTag_movieImg = document.createElement('img');
                let new_pTag_movieCd = document.createElement('p');
                let new_pTag_movieNm = document.createElement('p');
                let new_pTag_movieOpenDt = document.createElement('p');

                new_pTag_movieRank.append(`${i}`);
                new_pTag_movieImg.append(`이미지`);
                new_pTag_movieCd.append(`영화 코드 : ${movieJson[i].movieCd}`);
                new_pTag_movieNm.append(`영화이름: ${movieJson[i].movieNm}`);
                new_pTag_movieOpenDt.append(`개봉일: ${movieJson[i].openDt}`);

                new_pTag.append(new_pTag_movieRank);
                new_pTag.append(new_pTag_movieImg);
                new_pTag.append(new_pTag_movieCd);
                new_pTag.append(new_pTag_movieNm);
                new_pTag.append(new_pTag_movieOpenDt);


                plusSection.appendChild(new_pTag);
            }

        });
}