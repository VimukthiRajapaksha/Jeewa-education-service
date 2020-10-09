<!doctype html>
<html lang="en">
<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
  <script src="https://kit.fontawesome.com/dc483541e9.js" crossorigin="anonymous"></script>
  <style>
  	h3 > span {
    	color : #e2ded3;
    }
  </style>
</head>
<body>

  <div class="container">
    <!-- Start Header -->
    
    <!-- End Header -->

    <!-- Start Body -->
    <div class="d-flex p-3 justify-content-center flex-column">

      <!-- Start list event page -->
      <div class="container w-75 bg-light">
      <!-- If uni list is not null -->
        <div th:if="${universityList != null and !universityList.empty}">
        	<div class="row row-cols-4">
          		<div class="card col m-3" th:each="university : ${universityList}" >
            		<p th:text="${university.id}"></p>
   					<p th:text="${university.name}"></p>
   					<p th:text="${university.country}"></p>
          		</div>
        	</div>
        </div>
		
		<!-- If uni list is not null -->
        <div th:if="${degreeList != null and !degreeList.empty}">
        	<div class="row row-cols-4">
          		<div class="card col m-3" th:each="degree : ${degreeList}" >
            		<p th:text="${degree.id}"></p>
   					<p th:text="${degree.name}"></p>
   					<p th:text="${degree.university.id}"></p>
          		</div>
        	</div>
        </div>
		
		<div th:if="${message != null}">
			<div class="alert alert-primary" role="alert" th:text="${message}"></div>
        </div>
        <!-- uni get -->
		<div th:if="${university != null}">
   			<p th:text="${university.id}"></p>
   			<p th:text="${university.name}"></p>
   			<!--<p th:text="${university.country}"></p> -->
		</div>
      </div>

      <!-- End list event page -->

    </div>
    <!-- End Body -->
  </div>


  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
  <!-- Optional JavaScript -->
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
</body>
</html>