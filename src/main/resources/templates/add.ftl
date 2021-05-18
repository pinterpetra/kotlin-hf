<#-- @ftlvariable name="entries" type="kotlin.collections.List<com.jetbrains.handson.website.BlogEntry>" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Series Tracker</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/layout.css">
</head>
<body style="text-align: center; font-family: sans-serif">

<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand">
            <img class="icon" src="/static/filmleader.jpg">
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active"><b class="nav-link">Series Tracker </b></li>
            </ul>
        </div>
    </nav>
</header>

<hr>
<div>
    <p><i>You can add a new series here!</i></p>
    <form action="/submit" method="post">
        <input type="text" name="headline" valu="asd" placeholder="Name of the series">
        <input type="number" name="all" placeholder="Number of episodes">
        <input type="number" name="seen" placeholder="Number of seen episodes">
        <button type="submit">Add</button>
    </form>
</div>
<hr>
<a href="/">Go back to your list</a>

</body>
</html>