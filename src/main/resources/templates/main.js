<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>칵테일 레시피</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="styles.css">
</head>
<body class="bg-black text-white">
<!-- 헤더 -->
<header class="bg-dark d-flex align-items-center" style="height: 150px;"> 
    <div class="container" style="max-width: 100%; height: 80%; ">
        <div class="banner-container" style="width: 100%; height:auto; margin: 0 auto;">
            <img src="../static/images/head_banner.png" alt="홈으로">
        </div>
    </div>
</header>

<!-- 메인 콘텐츠 -->
<main>
    <section class="search-section text-center py-5" style="background: url('../static/images/main_search_background.png') center/cover no-repeat; height: 400px;">
        <div class="container">
            <h1 class="display-5 text-white mb-4">칵테일을 검색하세요</h1>
            <div class="d-flex justify-content-center align-items-center mb-3">
                <div class="input-group w-75">
                    <input type="text" class="form-control" id="cocktailSearch" placeholder="칵테일 이름이나 카테고리를 입력하세요">
                    <button class="btn btn-primary" onclick="searchCocktails()">검색</button>
                </div>
                <button class="btn btn-secondary ms-2" data-bs-toggle="modal" data-bs-target="#filterModal">
                    <img src="../static/images/filter-icon.png" alt="필터" style="height: 20px; width: 20px;">
                </button>
            </div>
        </div>
    </section>

    <section class="recommendation-section py-5">
        <div class="container">
            <h2 class="text-center mb-4">추천 칵테일</h2>
            <div id="cocktail-container" class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-4">
                <!-- 동적으로 카드들이 추가됩니다 -->
            </div>
        </div>
    </section>
</main>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    let currentPage = 1; // 현재 페이지
    const pageSize = 8; // 한 번에 로드할 칵테일 개수
    let isLoading = false; // 중복 요청 방지
    let hasMoreData = true; // 추가 데이터가 있는지 여부

    // 스크롤 이벤트 핸들러
    window.addEventListener("scroll", () => {
        if (isLoading || !hasMoreData) return;

        // 스크롤이 페이지 하단 근처에 도달했을 때
        if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 200) {
            loadMoreCocktails();
        }
    });

    // 데이터 로드 함수
    async function loadMoreCocktails() {
        isLoading = true;
        try {
            const response = await fetch(`/api/cocktails?page=${currentPage}&size=${pageSize}`);
            if (!response.ok) throw new Error("데이터를 가져오는 데 실패했습니다.");

            const cocktails = await response.json();
            if (cocktails.length < pageSize) {
                hasMoreData = false; // 더 이상 데이터가 없음
            }

            renderCocktailCards(cocktails);
            currentPage++;
        } catch (error) {
            console.error(error);
        } finally {
            isLoading = false;
        }
    }

    // 카드 렌더링 함수
    function renderCocktailCards(cocktails) {
        const container = document.getElementById("cocktail-container");

        cocktails.forEach(cocktail => {
            const card = document.createElement("div");
            card.className = "col";

            card.innerHTML = `
                <div class="card bg-dark text-white">
                    <img src="${cocktail.cocktail_image}" class="card-img-top" alt="${cocktail.cocktail_name}" />
                    <div class="card-body">
                        <h5 class="card-title">${cocktail.cocktail_name}</h5>
                        <p class="card-text">${cocktail.cocktail_easylore}</p>
                    </div>
                </div>
            `;
            container.appendChild(card);
        });
    }

    // 페이지 로드시 처음 몇 개의 칵테일 로드
    document.addEventListener("DOMContentLoaded", () => {
        loadMoreCocktails();
    });
</script>

</body>
</html>
