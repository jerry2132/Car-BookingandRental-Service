 
  function previewFile() {
            var fileInput = document.getElementById('file-input');
            var previewContainer = document.getElementById('preview-container');
            var previewImage = document.getElementById('preview-image');

            var file = fileInput.files[0];
            var reader = new FileReader();

            reader.onloadend = function () {
                previewImage.src = reader.result;
                previewContainer.style.display = 'block';
            };

            if (file) {
                reader.readAsDataURL(file);
            } else {
                previewImage.src = '';
                previewContainer.style.display = 'none';
            }
        }