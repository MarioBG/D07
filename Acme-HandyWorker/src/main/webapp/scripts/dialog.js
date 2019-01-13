function showDialog(_id, sucess, _dat, url) {
	$.get(url + '?' + _dat, function(data) {
		sucess.call(null, JSON.parse(data));
		$('#' + _id).modal('show');
	});
}

function showDialogNotRequest(_id) {
	$('#' + _id).modal('show');
}

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

function showDialogAprove(_id, accept, deny, row) {
	$('#' + _id).modal({
		closable : false,
		onDeny : function() {
			if(deny != undefined) {
				deny.call(null, row);
			}
			return true;
		},
		onApprove : function() {
			accept.call(null, row);
			return false;
		}
	}).modal('show');
}
