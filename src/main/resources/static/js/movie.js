const drawStar = (target) => {
    document.querySelector(`.star span`).style.width = `${target.value * 10}%`;
    let frm = document.getElementById("star_frm");
    frm.onsubmit = false;
    frm.submit();
}