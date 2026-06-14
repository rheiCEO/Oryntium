<?php
/**
 * The main template file
 *
 * @package ORYNTIUM
 * @since 1.0.0
 */

get_header();
?>

<main id="main" class="site-main">
    <?php
    // Check if home page template exists
    if (is_front_page()) {
        get_template_part('template-parts/content', 'home');
    } else {
        // Default WordPress loop
        if (have_posts()) :
            while (have_posts()) :
                the_post();
                get_template_part('template-parts/content', get_post_type());
            endwhile;
            
            the_posts_navigation();
        else :
            get_template_part('template-parts/content', 'none');
        endif;
    }
    ?>
</main>

<?php
get_footer();







