console.log("Script loaded!");

window.onload = function() {
    var reviewForm = document.getElementById('reviewForm');
    reviewForm.addEventListener('submit', function(event) {
        event.preventDefault();

        var name = document.getElementById('name').value;
        var comment = document.getElementById('comment').value;

        var albumId = reviewForm.getAttribute('data-album-id');

        fetch(`/albums/${albumId}/reviews`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: name,
                comment: comment
            })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            window.location.reload(); // Reload the page to see the new review
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
    });

    var ratingForm = document.getElementById('ratingForm');
    ratingForm.addEventListener('submit', function(event) {
        event.preventDefault();

        var rating = document.getElementById('rating').value;
        var albumId = ratingForm.getAttribute('data-album-id');

        fetch(`/albums/${albumId}/rate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(rating)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            window.location.reload(); // Reload the page to update the average rating
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
    });
}



