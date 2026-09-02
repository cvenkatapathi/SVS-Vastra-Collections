// STICKY / SCROLLED NAVBAR

window.addEventListener("scroll", function () {

    const navbar = document.querySelector(".navbar");

    if (!navbar) {
        return;
    }

    navbar.classList.toggle(
        "scrolled",
        window.scrollY > 80
    );

    navbar.classList.toggle(
        "sticky",
        window.scrollY > 50
    );

});

// SCROLL TO TOP

const topBtn = document.getElementById("topBtn");

if (topBtn) {

    window.addEventListener("scroll", function () {

        if (
            document.body.scrollTop > 300 ||
            document.documentElement.scrollTop > 300
        ) {

            topBtn.style.display = "block";

        } else {

            topBtn.style.display = "none";

        }

    });


    topBtn.addEventListener("click", function () {

        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });

    });

}

// HERO SLIDER

const slides = document.querySelectorAll(".slide");

let currentSlide = 0;


function showSlide(index) {

    slides.forEach(function (slide) {

        slide.classList.remove("active");

    });


    if (slides.length > 0) {

        slides[index].classList.add("active");

    }

}


if (slides.length > 0) {

    setInterval(function () {

        currentSlide++;

        if (currentSlide >= slides.length) {

            currentSlide = 0;

        }

        showSlide(currentSlide);

    }, 4000);

}

// ACTIVE NAVIGATION LINK

document.addEventListener("DOMContentLoaded", function () {

    const currentPath =
        window.location.pathname;


    document
        .querySelectorAll(".nav-links a")
        .forEach(function (link) {

            if (
                link.getAttribute("href") === currentPath
            ) {

                link.classList.add("active");

            }

        });

});

// CONTACT FORM

const contactForm =
    document.querySelector(".contact-form form");


if (contactForm) {

    contactForm.addEventListener("submit", function (e) {

        e.preventDefault();


        const name =
            contactForm
                .querySelector("input[type='text']")
                .value
                .trim();


        const email =
            contactForm
                .querySelector("input[type='email']")
                .value
                .trim();


        const message =
            contactForm
                .querySelector("textarea")
                .value
                .trim();


        if (
            name === "" ||
            email === "" ||
            message === ""
        ) {

            alert(
                "Please fill in all required fields."
            );

            return;

        }


        alert(
            "Thank you! Your message has been sent successfully."
        );


        contactForm.reset();

    });

}

// CONTACT CARD ANIMATION

const cards =
    document.querySelectorAll(".contact-card");


cards.forEach(function (card) {

    card.addEventListener("mouseenter", function () {

        card.style.transform =
            "translateY(-10px)";

    });


    card.addEventListener("mouseleave", function () {

        card.style.transform =
            "translateY(0)";

    });

});

// WISHLIST STORAGE

const WISHLIST_KEY = "svsWishlist";


function getWishlist() {

    try {

        const stored =
            localStorage.getItem(WISHLIST_KEY);


        if (!stored) {

            return [];

        }


        const wishlist =
            JSON.parse(stored);


        return Array.isArray(wishlist)
            ? wishlist
            : [];

    } catch (error) {

        console.error(
            "Unable to read wishlist:",
            error
        );

        return [];

    }

}


function saveWishlist(wishlist) {

    localStorage.setItem(
        WISHLIST_KEY,
        JSON.stringify(wishlist)
    );

}

// ADD / REMOVE FROM SHOP

document.addEventListener(
    "DOMContentLoaded",
    function () {

        const productCards =
            document.querySelectorAll(
                ".shop-products .product-card"
            );


        if (productCards.length === 0) {

            return;

        }


        let wishlist =
            getWishlist();


        productCards.forEach(function (card) {

            const button =
                card.querySelector(
                    ".wishlist-toggle"
                );


            if (!button) {

                return;

            }


            const icon =
                button.querySelector("i");


            const product = {

                id: card.dataset.productId,

                name: card.dataset.productName,

                price: card.dataset.productPrice,

                image: card.dataset.productImage

            };


            function updateHeart() {

                const exists =
                    wishlist.some(function (item) {

                        return item.id === product.id;

                    });


                if (exists) {

                    icon.classList.remove(
                        "fa-regular"
                    );

                    icon.classList.add(
                        "fa-solid"
                    );


                    button.classList.add(
                        "wishlist-added"
                    );


                    button.setAttribute(
                        "aria-label",
                        "Remove from wishlist"
                    );


                    button.setAttribute(
                        "title",
                        "Remove from wishlist"
                    );

                } else {

                    icon.classList.remove(
                        "fa-solid"
                    );

                    icon.classList.add(
                        "fa-regular"
                    );


                    button.classList.remove(
                        "wishlist-added"
                    );


                    button.setAttribute(
                        "aria-label",
                        "Add to wishlist"
                    );


                    button.setAttribute(
                        "title",
                        "Add to wishlist"
                    );

                }

            }


            updateHeart();


            button.addEventListener(
                "click",
                function () {

                    const index =
                        wishlist.findIndex(
                            function (item) {

                                return item.id ===
                                    product.id;

                            }
                        );


                    if (index === -1) {

                        wishlist.push(product);

                    } else {

                        wishlist.splice(
                            index,
                            1
                        );

                    }


                    saveWishlist(wishlist);

                    updateHeart();

                }
            );

        });

    }
);

// RENDER WISHLIST

document.addEventListener(
    "DOMContentLoaded",
    function () {

        const wishlistContainer =
            document.getElementById(
                "wishlistProducts"
            );


        const emptyMessage =
            document.getElementById(
                "emptyWishlistMessage"
            );


        if (
            !wishlistContainer ||
            !emptyMessage
        ) {

            return;

        }


        function renderWishlist() {

            const wishlist =
                getWishlist();


            wishlistContainer.innerHTML = "";


            if (wishlist.length === 0) {

                emptyMessage.style.display =
                    "block";

                return;

            }


            emptyMessage.style.display =
                "none";


            wishlist.forEach(function (product) {

                const card =
                    document.createElement(
                        "div"
                    );


                card.className =
                    "product-card";


                card.innerHTML = `

                    <button
                        type="button"
                        class="remove-wishlist-btn"
                        data-product-id="${product.id}"
                        title="Remove from wishlist"
                        aria-label="Remove from wishlist">

                        <i class="fa-solid fa-xmark"></i>

                    </button>


                    <img
                        src="${product.image}"
                        alt="${product.name}">


                    <h3>
                        ${product.name}
                    </h3>


                    <p>
                        ₹${product.price}
                    </p>


                    <form
                        action="/cart/add"
                        method="post">

                        <input
                            type="hidden"
                            name="name"
                            value="${product.name}">


                        <input
                            type="hidden"
                            name="price"
                            value="${product.price}">


                        <input
                            type="hidden"
                            name="image"
                            value="${product.image}">


                        <input
                            type="hidden"
                            name="quantity"
                            value="1">


                        <button
                            type="submit">

                            Add to Cart

                        </button>

                    </form>

                `;


                wishlistContainer.appendChild(
                    card
                );

            });


            document
                .querySelectorAll(
                    ".remove-wishlist-btn"
                )
                .forEach(function (button) {

                    button.addEventListener(
                        "click",
                        function () {

                            const productId =
                                button.dataset.productId;


                            let wishlist =
                                getWishlist();


                            wishlist =
                                wishlist.filter(
                                    function (item) {

                                        return item.id !==
                                            productId;

                                    }
                                );


                            saveWishlist(
                                wishlist
                            );


                            renderWishlist();

                        }
                    );

                });

        }


        renderWishlist();

    }
);