window.onload = function() {
    document.getElementById('searchBox').addEventListener('input', function() {
        var query = this.value;
        if(query.length > 0) { // search when query is 1 or more characters
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

                    // create an unordered list element
                    var resultList = document.createElement('ul');

                    // append new results as list items
                    data.forEach(album => {
                        var listItem = document.createElement('li');
                        var link = document.createElement('a');
                        link.href = '/albums/' + album.musicBrainzId;
                        link.textContent = album.title;
                        link.className = 'searchResult'; // give it a class name for styling
                        listItem.appendChild(link);
                        resultList.appendChild(listItem);
                    });

                    // append the list to the search results div
                    document.getElementById('searchResults').appendChild(resultList);
                })
                .catch(error => {
                    console.error('There has been a problem with your fetch operation:', error);
                });
        } else {
            // clear the results
            document.getElementById('searchResults').innerHTML = '';
        }
    });
};


