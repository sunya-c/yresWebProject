window.onload = function() {
    let toggleMenu = document.querySelector("#toggleMenu");
    /** @type {HTMLInputElement} */
    let toggleMenuCheckbox = toggleMenu.querySelector("#toggleMenuCheckbox");
    let preventActionScreen = document.querySelector("#preventActionScreen");

    toggleMenuCheckbox.addEventListener("change", function() {
        let checked = toggleMenuCheckbox.checked;
        if (checked)
        {
            preventActionScreen.classList.add("show");
            preventActionScreen.addEventListener("pointerup", clickToClose);
        }
        else
        {
            preventActionScreen.classList.remove("show");
            preventActionScreen.removeEventListener("pointerup", clickToClose);
        }
    });

    function clickToClose() {
        toggleMenuCheckbox.checked = false;
        toggleMenuCheckbox.dispatchEvent(new Event("change"));
    }
}