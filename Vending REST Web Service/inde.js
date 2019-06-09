$(document).ready(function () {
    //alert("Wired up!");
    $(document).on("click", ".character-card", function (e) {
        //alert("This has been clicked!");
        e.preventDefault();

        var btn = $(this);
        console.log(btn);
    });
});