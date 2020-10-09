<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>University of Sydney</title>

<!-- Custom fonts for this template-->
  <link href="../static/admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="../static/admin/css/sb-admin-2.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">

	<div>
		<div class="row">
			<div class="col p-5">
				<div class="row p-3">
					<h3 style="font-size:50px;" th:text="${university.name}"></h3>
				</div>
				<div class="row p-3">
					<p>University of Sydney is an Australian public research university in Sydney. Founded in 1850, it is Australias' first university 
	and regarded as one of the world's leading universities. Campus is ranked in the top 10 of the world's most beautiful universities. 
	This has nine faculties and university schools, through which it offers bachelor, master and doctoral degrees. World class 
	teaching and learning and a vibrant campus life, it is a place where students can attain widely recorganized and respected 
	qualifications. This university offers Australia's broadest range of displine and its' research focuses on finding solutions 
	to society's biggest challenges.</p>
				</div>
			</div>
			<div class="col p-5 my-3">
				<div class="row p-3"></div>
				<div class="row p-3">
					<img  th:src="@{'data:image/png;base64,'+${university.image}}" height="250" width="500" />
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="row m-2 border rounded">
		<div class="row p-5 w-100">
			<div class="col-9">
				<h4>Degree Programs</h4>
			</div>
			<div class="col d-flex">
				<form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
            		<div class="input-group">
              			<input type="text" class="form-control bg-light border-0 small" placeholder="Search degree..." aria-label="Search" aria-describedby="basic-addon2">
              			<div class="input-group-append">
                			<button class="btn btn-primary" type="button">
                  				<i class="fas fa-search fa-sm"></i>
                			</button>
              			</div>
            		</div>
          		</form>
			</div>
		</div>
		<div class="row p-5">
			<div class="row row-cols-4 justify-content-around">
          			<div class="card col m-3" th:each="degree : ${degreeList}" style="width: 18rem;">
            			<div class="card-img-top p-2 bg-gradient-primary" style="height: 100px; text-align:center;margin-top: auto;font-size: 50px;"><b>D</b></div>
            			<div class="card-body">
            				<a th:href="@{'/degrees/'+${degree.id}}">
              					<h5 class="card-title text-center" th:text="${degree.name}"></h5>
              				</a>
              				<h6 class="card-subtitle mb-2 text-muted text-center" th:text="${degree.fee}"></h6>
              				<p class="card-text" th:text="${degree.description}" style="overflow: hidden;display: -webkit-box;-webkit-line-clamp: 3;-webkit-box-orient: vertical;">
              				</p>
              				
              				<a href="update_degree?id=${degree.id}" class="btn btn-info btn-sm">
                      			<i class="fas fa-edit"></i>
                    			<span class="text">Update</span>
                  			</a>
                  			<a href="delete_degree?id=${degree.id}" class="btn btn-danger btn-sm">
	                            <i class="fas fa-trash"></i>
    	                      <span class="text">Delete</span>
        	                </a>
            			</div>
          			</div>
        		</div>
		</div>
	</div>
	
	</div>
	<!-- Bootstrap core JavaScript-->
  <script src="../adminAssets/vendor/jquery/jquery.min.js"></script>
  <script src="../adminAssets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="../adminAssets/vendor/jquery-easing/jquery.easing.min.js"></script>
  
  <!-- Custom scripts for all pages-->
  <script src="../adminAssets/js/sb-admin-2.min.js"></script>
  
  <!-- Page level plugins -->
  <script src="../adminAssets/vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="../adminAssets/vendor/datatables/dataTables.bootstrap4.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="../adminAssets/js/demo/datatables-demo.js"></script>
</body>
</html>