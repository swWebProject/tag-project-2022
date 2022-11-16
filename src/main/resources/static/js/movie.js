const drawStar = (target) => {
    document.querySelector(`.star span`).style.width = `${target.value * 10}%`;
    let frm = document.getElementById("star_frm");
    frm.onsubmit = false;
    frm.submit();
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