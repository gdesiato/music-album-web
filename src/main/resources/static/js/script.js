console.log("Script loaded!");

window.onload = function() {
    var reviewRatingForm = document.getElementById('reviewRatingForm');
    reviewRatingForm.addEventListener('submit', function(event) {
        event.preventDefault();

        var name = document.getElementById('name').value;
        var comment = document.getElementById('comment').value;
        var rating = document.querySelector('input[name="rating"]:checked').value;

        var albumId = reviewRatingForm.getAttribute('data-album-id');

        fetch(`/albums/${albumId}/reviews-ratings`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: name,
                comment: comment,
                rating: rating
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
            window.location.reload(); // Reload the page to see the updated review and rating
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
    });
};



