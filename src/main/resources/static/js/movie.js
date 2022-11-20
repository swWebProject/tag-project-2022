const clickStar = (target, movieCd) => {

    const star = target.value;

    const starJson = {
        movieId: movieCd,
        userId: null,
        rating: star
    }

    fetch('/api/post/movie/rating', {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(starJson)
    })
        .then((response) => response.json())
        .then((data) => console.log(data + " post succes!!"))
        .catch(function (err){
            console.log(err)
        })
}

/** 보고싶어요 버튼 */
const wantButton = document.querySelector('#wantButton');

/** 보고싶어요 버튼 클릭 리스너 */
wantButton.addEventListener('click', function () {

    const wantData = {
        userId: document.querySelector("#wantUserId").value,
        movieId: document.querySelector("#wantMovieId").value,
    }

    console.log(wantData);

    fetch('/api/post/wanting', {
        method: "post",
        body: JSON.stringify(wantData),
        headers: { 'Content-Type': 'application/json' }
    })
        .then((response) => response.json())
        .then(response => {
            console.log("Post movieSeeing Connected");
            console.log(response);
        })
        .catch((error) => {
            console.error('실패 ', error)
        })

})


/** 보는 중 버튼 */
const watchButton = document.querySelector('#watchButton');

/** 보는 중 버튼 클릭 리스너 */
watchButton.addEventListener('click', function () {
    const wantData = {
        userId: document.querySelector("#watchUserId").value,
        movieId: document.querySelector("#watchMovieId").value,
    }

    console.log(wantData);

    fetch('/api/post/watching', {
        method: "post",
        body: JSON.stringify(wantData),
        headers: { 'Content-Type': 'application/json' }
    })
        .then((response) => response.json())
        .then(response => {
            console.log("Post movieSeeing Connected");
            console.log(response);
        })
        .catch((error) => {
            console.error('실패 ', error)
        })
})

let checkNum = 0;
function check(num){
    let obj = document.querySelectorAll('input[type="radio"]');
    let n = 0;

    for(let i = 10; i >= 0; i--) {
        if(n == num) {
            num = i;
            break;
        }
        n++;
    }

    if(checkNum == num){
        obj[num].checked = false;
        checkNum = null;
    } else{
        checkNum = num;
    }
}