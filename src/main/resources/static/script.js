const checkBox = document.querySelector(".checkbox");
const input = document.querySelector(".admin-key-input");
checkBox.addEventListener('change', function() {
    input.classList.toggle('admin-key-input--visible');
})