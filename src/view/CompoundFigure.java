package view;

/**
 * A figure composed of one or more figures
 *
 * @author Pascale Launay
 *
 * @inv getFigures() != null && getFigures().length > 0
 */
public class CompoundFigure extends Figure
{

    private Figure[] figures;

    /**
     * Initialize a compound figure composed of the gievn figures
     *
     * @param figures the figures that compose the compound figure
     *
     * @pre figures != null && figures.length > 0
     */
    public CompoundFigure(Figure[] figures)
    {
        super();
        assert figures != null && figures.length > 0 : "empty figures list";
        this.figures = figures;
        invariant();
    }

    //------------------------------------------------------------------------
    // Draw
    //------------------------------------------------------------------------
    /**
     * {@inheritDoc }
     */
    @Override
    public void draw()
    {
        for (Figure figure : this.figures) {
            figure.draw();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void erase()
    {
        for (Figure figure : this.figures) {
            figure.erase();
        }
    }

    //------------------------------------------------------------------------
    // Getters
    //------------------------------------------------------------------------
    /**
     * Give the figures that compose the current figure
     *
     * @return the figures that compose this figure
     */
    public Figure[] getFigures()
    {
        return this.figures;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getX()
    {
        // look for the smallest x
        int x = this.figures[0].getX();
        for (int i = 1; i < this.figures.length; i++) {
            if (this.figures[i].getX() < x) {
                x = this.figures[i].getX();
            }
        }
        return x;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getY()
    {
        // look for the smallest y
        int y = this.figures[0].getY();
        for (int i = 1; i < this.figures.length; i++) {
            if (this.figures[i].getY() < y) {
                y = this.figures[i].getY();
            }
        }
        return y;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getWidth()
    {
        // look for the greatest x
        int x = this.figures[0].getX();
        for (int i = 1; i < this.figures.length; i++) {
            if (this.figures[i].getX() > x) {
                x = this.figures[i].getX();
            }
        }
        // return the difference between the greatest and the smallest x
        return x - this.getX();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getHeight()
    {
        // look for the greatest x
        int y = this.figures[0].getY();
        for (int i = 1; i < this.figures.length; i++) {
            if (this.figures[i].getY() > y) {
                y = this.figures[i].getY();
            }
        }
        // return the difference between the greatest and the smallest y
        return y - this.getY();
    }

    //------------------------------------------------------------------------
    // Setters
    //------------------------------------------------------------------------
    /**
     * {@inheritDoc }
     */
    @Override
    public void move()
    {
        for (Figure figure : this.figures) {
            figure.move();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void move(int dx, int dy)
    {
        for (Figure figure : this.figures) {
            figure.move(dx, dy);
        }
    }

    //------------------------------------------------------------------------
    // Invariant
    //------------------------------------------------------------------------
    /**
     * Check the class invariant
     */
    @Override
    protected void invariant()
    {
        super.invariant();
        assert this.figures != null && this.figures.length > 0 : "Invariant violated: empty figures list";
    }
}
