document.getElementById('searchBox').addEventListener('input', function() {
    var query = this.value;
    if(query.length > 1) { // only search when query is 2 or more characters
        fetch(`/api/v1/albums/search?q=${query}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const contentType = response.headers.get("content-type");
                if (!contentType || !contentType.includes("application/json")) {
                    throw new TypeError("Oops, we haven't got JSON!");
                }
                return response.json();
            })
            .then(data => {
                // clear the results
                document.getElementById('searchResults').innerHTML = '';

                // append new results
                data.forEach(album => {
                    var node = document.createElement('div');
                    node.textContent = album.title;
                    document.getElementById('searchResults').appendChild(node);
                });
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    } else {
        // clear the results
        document.getElementById('searchResults').innerHTML = '';
    }
});
