function approveBooking(userId) {
    fetch('/admin/bookingStatusApprove/' + userId, {
        method: 'POST'
      
    })
    
}

function rejectBooking(userId) {
    fetch('/admin/bookingStatusReject/' + userId, {
        method: 'POST'
     
    })
    
}
