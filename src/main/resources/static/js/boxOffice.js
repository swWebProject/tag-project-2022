/** 일별 박스오피스 호출 API fetch 함수 ver 1 */
function callFetchMovie() {
    fetch("/api/search/dailyBoxOffice")
        .then(response => response.json())
        .then(function (json) {
            for (let i = 0; i < json.length; i++) {
                let movieJson = json[i].movies;

                console.log(movieJson);
                let plusSection = document.getElementById('section_main_div');

                let new_pTag = document.createElement('p');
                new_pTag.setAttribute('id', 'new_pTag');

                let new_aTag = document.createElement('a');
                new_aTag.setAttribute('id', 'new_aTag');
                new_aTag.setAttribute('href', `/movie/${movieJson.movieCd}`);

                let new_pTag_movieRank = document.createElement('p');
                let new_pTag_movieImg = document.createElement('img');
                let new_pTag_movieNm = document.createElement('p');
                let new_pTag_movieOpenDt = document.createElement('p');
                let new_pTag_movieNation = document.createElement('p');

                new_pTag_movieRank.append(`${i + 1}`);
                new_pTag_movieNm.append(`${movieJson.movieNm}`);
                new_pTag_movieOpenDt.append(`${movieJson.openDt.substr(0, 4)}`);
                new_pTag_movieNation.append(`${movieJson.nationAlt}`);

                new_pTag.append(new_pTag_movieRank);
                new_aTag.append(new_pTag_movieImg);
                new_pTag.append(new_aTag);
                new_pTag.append(new_pTag_movieNm);
                new_pTag.append(new_pTag_movieOpenDt);
                new_pTag.append(new_pTag_movieNation);

                new_pTag_movieRank.setAttribute('id', 'movie_rank');
                new_pTag_movieNm.setAttribute('id', 'movie_title');
                new_pTag_movieImg.setAttribute('type', 'image');
                new_pTag_movieImg.setAttribute('src', `${movieJson.poster}`);

                plusSection.appendChild(new_pTag);
            }

        });
}

callFetchMovie();