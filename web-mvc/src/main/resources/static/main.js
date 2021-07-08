function submitStatus(index) {
    let checkboxStatus = document.getElementById("checkBoxStatus" + index);
    if (checkboxStatus.checked) {
        checkboxStatus.value = 'DONE'
    } else {
        checkboxStatus.value = 'ACTIVE'
    }
    document.getElementById('change-status' + index).submit();
}