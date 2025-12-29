window.onload = function() {
    let toggleMenu = document.querySelector("#toggleMenu");
    let leftContent = document.querySelector("#leftContent");
    let preventActionScreen = document.querySelector("#preventActionScreen");
    let body = document.querySelector("body");

    function clickToClose(event) {
        toggleMenu.dispatchEvent(new Event("pointerup"))
    }

    toggleMenu.addEventListener("pointerup", function() {
        if (leftContent.classList.contains("show"))
        {
            leftContent.classList.remove("show");
            preventActionScreen.classList.remove("show");
            body.classList.remove("preventScrolling");
        }
        else
        {
            preventActionScreen.classList.add("show");
            leftContent.classList.add("show");
            body.classList.add("preventScrolling");

            preventActionScreen.addEventListener("pointerup", clickToClose);
        }
    });
    
}