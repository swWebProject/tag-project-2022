function callFetchMypageSecond() {
    fetch('/api/get/wanting')
        .then(response => response.json())
        .then(function (json) {
            console.log(json);
            for (let i = 0; i < json.length; i++) {
                let myWant = json[i].movie;

                let myWantMovieNm = myWant.movieNm;
                let myWantNationAlt = myWant.nationAlt;
                let myWantOpenDt = myWant.openDt;
                let myWantImg = myWant.poster;

                let user_want = document.getElementById('mypage_want');
                let new_divTag = document.createElement('div');

                let new_aTag = document.createElement('a');
                new_aTag.setAttribute('class', 'new_aTag');
                new_aTag.setAttribute('href', `/movie/${myWant.movieCd}`);

                let new_pTag_wImg = document.createElement('img');
                let new_pTag_wMovieNm = document.createElement('p');
                let new_pTag_OpenNation = document.createElement('p');

                new_pTag_wImg.setAttribute('type', 'image');
                new_pTag_wImg.setAttribute('src', `${myWantImg}`);
                new_pTag_wMovieNm.setAttribute('class', 'wmovie_title');
                new_pTag_OpenNation.setAttribute('class', 'wnation_dt');

                new_pTag_wMovieNm.append(`${myWantMovieNm}`);
                new_pTag_OpenNation.append(`${myWantOpenDt.substr(0, 4)}, ${myWantNationAlt}`);

                new_divTag.append(new_pTag_wImg);
                new_divTag.append(new_pTag_wMovieNm);
                new_divTag.append(new_pTag_OpenNation);
                new_aTag.append(new_divTag);

                user_want.appendChild(new_aTag);
            }
        })
        .catch((err) => {
            console.log(err);
        })
}

function callFetchMypageThird() {
    fetch('/api/get/watching')
        .then(response => response.json())
        .then(function (json) {
            console.log(json);
            for (let i = 0; i < json.length; i++) {
                let myWatch = json[i].movie;

                let myWatchMovieNm = myWatch.movieNm;
                let myWatchNationAlt = myWatch.nationAlt;
                let myWatchOpenDt = myWatch.openDt;
                let myWatchImg = myWatch.poster;

                let user_watching = document.getElementById('mypage_watching');
                let new_divTag = document.createElement('div');

                let new_aTag = document.createElement('a');
                new_aTag.setAttribute('class', 'new_aTag');
                new_aTag.setAttribute('href', `/movie/${myWatch.movieCd}`);

                let new_pTag_ingImg = document.createElement('img');
                let new_pTag_ingMovieNm = document.createElement('p');
                let new_pTag_ingOpenNation = document.createElement('p');

                new_pTag_ingImg.setAttribute('type', 'image');
                new_pTag_ingImg.setAttribute('src', `${myWatchImg}`);
                new_pTag_ingMovieNm.setAttribute('class', 'ingmovie_title');
                new_pTag_ingOpenNation.setAttribute('class', 'ingOpen_nation');

                new_pTag_ingMovieNm.append(`${myWatchMovieNm}`);
                new_pTag_ingOpenNation.append(`${myWatchOpenDt.substr(0, 4)}, ${myWatchNationAlt}`);

                new_divTag.append(new_pTag_ingImg);
                new_divTag.append(new_pTag_ingMovieNm);
                new_divTag.append(new_pTag_ingOpenNation);
                new_aTag.append(new_divTag);

                user_watching.appendChild(new_aTag);
            }
        })
        .catch((err) => {
            console.log(err);
        })
}

/** 마이페이지 정보 fetch 호출 함수 */
function callFetchMypageFirst() {
    fetch('/api/get/rating')
        .then(response => response.json())
        .then(function (json) {
            console.log(json);
            for (let i = 0; i < json.length; i++) {
                let myReview_movie = json[i].movie;
                let myReview_movie_movieNm = myReview_movie.movieNm;
                let myReview_movie_nation = myReview_movie.nationAlt;
                let myReview_movie_openDt = myReview_movie.openDt;
                let myReview_movie_avgRating = myReview_movie.averageRating;

                mypage_div = document.getElementById('mypage_comment');

                let new_aTag = document.createElement('a');
                new_aTag.setAttribute('class', 'new_aTag');
                new_aTag.setAttribute('href', `/movie/${myReview_movie.movieCd}`);

                new_mypage_review = document.createElement('div');
                new_mypage_review.setAttribute('class', 'mypage_review');

                new_pTag_title = document.createElement('p');
                new_pTag_information = document.createElement('p');
                new_hrTag = document.createElement('hr');
                new_pTag_comment = document.createElement('p');

                new_pTag_title.append(`${myReview_movie_movieNm}`);
                new_pTag_information.append(`${myReview_movie_openDt.substr(0, 4)}, ${myReview_movie_nation}, ★ ${myReview_movie_avgRating}`);

                new_pTag_title.setAttribute('class', 'mypage_review_in_title');
                new_pTag_information.setAttribute('class', 'mypage_review_in_information');
                new_hrTag.setAttribute('class', 'mypage_review_in_hr');
                new_pTag_comment.setAttribute('class', 'mypage_review_in_comment');

                new_mypage_review.append(new_pTag_title);
                new_mypage_review.append(new_pTag_information);
                new_mypage_review.append(new_hrTag);
                new_mypage_review.append(new_pTag_comment);
                new_aTag.append(new_mypage_review);

                mypage_div.appendChild(new_aTag);
            }
        })
        .catch((err) => {
            console.log(err);
        })
}