let api_key = "22f10dd3863febae7e67b8df14051d8b";
let req_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";

function loadData(){

    let movieNm = document.getElementById('moviNm').value.trim();
    let openDt = document.getElementById('openDt').value.trim();
    let audiAcc = document.getElementById('audiAcc').value.trim();
    let showRange = document.getElementById('showRange').value.trim();
    movieNm = encodeURI(movieNm);

    console.log(`${movieNm} ${openDt} ${audiAcc} ${showRange}`);


// JSON

    let url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=" + api_key +
    "&movieNm" + movieNm + "&audiAcc" + audiAcc + "&showRange" + showRange;

    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            parseJSON(this.responseText);
        }
    };

    xhttp.open("GET", url, true);
    xhttp.send();

}

function parseJSON(jsonText){
    let jsonObj = JSON.parse(jsonText); 

    let movie = jsonObj.movieListResult.movieList;
    let i;

    let table = "<tr><th>영화명</th><th>개봉일</th><th>감독</th><th>누적관객수</th></tr>";
    for(i = 0; i < movie.length; i++){
        table += "<tr>";
        table += "<td>" + movie[i].movieNm + "</td>";
        table += "<td>" + movie[i].openDt + "</td>";
        table += "<td>" + movie[i].audiAcc + "</td>";
        table += "<td>" + movie[i].showRange + "</td>";
        table += "</tr>";
    } // for end

    document.getElementById("home_main.html").innerHTML = table;
}