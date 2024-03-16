document.addEventListener('DOMContentLoaded', function() {
    const menuIcon = document.querySelector('.menu-icon');
    const menu = document.querySelector('.menu');
    const exitButton = document.querySelector('.exit-button');

    menuIcon.addEventListener('click', function() {
        menu.classList.toggle('open');
        setTimeout(() => {
            menu.classList.toggle('active');
        }, 50);
    });

    exitButton.addEventListener('click', function() {
        menu.classList.remove('open');
        menu.classList.remove('active');
    });
});
