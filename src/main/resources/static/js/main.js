    function waitForImageToLoad(imageElement){
      return new Promise(resolve=>{imageElement.onload = resolve})
    }

    function formToJSON($form){
        var unindexed_array = $form.serializeArray();
        var indexed_array = {};
        $.map(unindexed_array, function(n, i){
            indexed_array[n['name']] = n['value'];
        });
               indexed_array
        return indexed_array;
    }

    function myFunction() {
        event.preventDefault(); // prevent this form from being submited
        console.log(formToJSON($('#radarForm')));
        $.ajax({
            url: '/image/radar_link',
            type: "POST",
            data: JSON.stringify(formToJSON($('#radarForm'))),
            contentType: 'application/json;charset=UTF-8', //sending
            dataType: 'text', //expecting
            success : function(result) {
                console.log(result);
                var myImage = document.getElementById('radar');
                myImage.src = result;
                waitForImageToLoad(myImage).then(()=>{
                    console.log('Loaded lol')
                });
            },
            error: function(jqXHR, textStatus, errorThrown) {
               console.log(textStatus, errorThrown);
            }
        })
    }

    function populateDropdownYears(control) {
        var ID = control.value;
        $.ajax({
            url: "http://localhost:8080/players/" + ID + "/allSeasons"
            }).then(function(data) {
                var $dropdown = $('#year');
                $dropdown.empty();
                $.each(data, function(){
                    $("<option />", {val: this, text: this}).appendTo($dropdown);
                });
        });
    }

    function init() {
        var dropdown = document.getElementById('playerId');
        console.log(dropdown);
        populateDropdownYears(dropdown);
    }

    window.onload = init;
